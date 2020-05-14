package demo.designpattern.responsibility.chain;

import java.util.LinkedList;

/**
 * @Description 责任链模式复杂版
 * @Author liuhu
 * @Date 2020/5/13 23:29
 **/
public class HardDemo {

    public static void main(String[] args) {
        WebFilterChain webFilterChain = new WebFilterChain()
                .setNext(new Filter1())
                .setNext(new Filter2());
        webFilterChain.doFilter(webFilterChain, new Request(), new Response());
    }

    interface WebFilter {
        void doFilter(WebFilterChain filterChain, Request request, Response response);
    }

    static class WebFilterChain implements WebFilter {
        LinkedList<WebFilter> linkedList = new LinkedList<>();

        WebFilterChain setNext(WebFilter webFilter) {
            linkedList.add(webFilter);
            return this;
        }

        WebFilterChain getNext() {
            linkedList.removeFirst();
            return this;
        }

        @Override
        public void doFilter(WebFilterChain filterChain, Request request, Response response) {
            linkedList.get(0).doFilter(this, request, response);
        }
    }

    public static class Filter1 implements WebFilter {
        @Override
        public void doFilter(WebFilterChain filterChain, Request request, Response response) {
            System.out.println(this.getClass().getName() + request);
            if (filterChain.linkedList.size() > 0) {
                filterChain.getNext().doFilter(filterChain, request, response);
            }
            System.out.println(this.getClass().getName() + response);
        }
    }

    public static class Filter2 implements WebFilter {
        @Override
        public void doFilter(WebFilterChain filterChain, Request request, Response response) {
            filterChain.linkedList.removeFirst();
            System.out.println(this.getClass().getName() + request);
            if (filterChain.linkedList.size() > 0) {
                filterChain.getNext().doFilter(filterChain, request, response);
            }
            System.out.println(this.getClass().getName() + response);
        }
    }

    static class Request {
        public String str = "Request";
    }

    static class Response {
        public String str = "Response";
    }
}
