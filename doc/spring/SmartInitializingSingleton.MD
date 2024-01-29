# 一、使用场景
实现SmartInitializingSingleton的接口后，当所有单例 bean 都初始化完成以后， Spring的IOC容器会回调该接口的
afterSingletonsInstantiated()方法。

主要应用场合就是在所有单例 bean 创建完成之后，可以在该回调中做一些事情，例如：

```java
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class MyRegister implements SmartInitializingSingleton {

    private ListableBeanFactory beanFactory;

    public MyRegister(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        String[] beanNames = beanFactory.getBeanNamesForType(IPerson.class);
        for (String s : beanNames) {
            System.out.println(s);
        }
    }

}
```

# 二、原理分析
在Spring容器启动时，通过SpringApplication.run()-->SpringApplication.refreshContext()-->SpringApplication.refresh() -->
AbstractApplicationContext.refresh()，在这个refresh()函数过程中会调用 finishBeanFactoryInitialization(beanFactory)
来提前初始化单例bean，具体方法是调用beanFactory.preInstantiateSingletons()，而这里的beanFactory实例实际为接口
ConfigurableListableBeanFactory 的实现类DefaultListableBeanFactory的实例对象。

查看到DefaultListableBeanFactory.preInstantiateSingletons()的源码如下：

```java
    @Override
    public void preInstantiateSingletons() throws BeansException {
        if (logger.isTraceEnabled()) {
            logger.trace("Pre-instantiating singletons in " + this);
        }
 
        // Iterate over a copy to allow for init methods which in turn register new bean definitions.
        // While this may not be part of the regular factory bootstrap, it does otherwise work fine.
        List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
 
        // Trigger initialization of all non-lazy singleton beans...
        for (String beanName : beanNames) {
            RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
            if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
                if (isFactoryBean(beanName)) {
                    Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
                    if (bean instanceof FactoryBean) {
                        final FactoryBean<?> factory = (FactoryBean<?>) bean;
                        boolean isEagerInit;
                        if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
                            isEagerInit = AccessController.doPrivileged((PrivilegedAction<Boolean>)
                                            ((SmartFactoryBean<?>) factory)::isEagerInit,
                                    getAccessControlContext());
                        }
                        else {
                            isEagerInit = (factory instanceof SmartFactoryBean &&
                                    ((SmartFactoryBean<?>) factory).isEagerInit());
                        }
                        if (isEagerInit) {
                            getBean(beanName);
                        }
                    }
                }
                else {
                    getBean(beanName);
                }
            }
        }
 
        // Trigger post-initialization callback for all applicable beans...
        for (String beanName : beanNames) {
            Object singletonInstance = getSingleton(beanName);
            if (singletonInstance instanceof SmartInitializingSingleton) {
                final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
                if (System.getSecurityManager() != null) {
                    AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                        smartSingleton.afterSingletonsInstantiated();
                        return null;
                    }, getAccessControlContext());
                }
                else {
                    smartSingleton.afterSingletonsInstantiated();
                }
            }
        }
    }
```
## 从源码分析中得知，当Spring将所有单例 bean 都实例初始化完成以后，如果存在实现SmartInitializingSingleton接口的bean，那么Spring还会调用到该bean的afterSingletonsInstantiated()方法。