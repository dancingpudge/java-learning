package demo.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * https://github.com/alibaba/fastjson/issues/942
 *
 * 1.2.23中问题已经修复
 * @author: LiuH
 * @date: 2024/4/8 09:37
 */
public class TimeJsonTest {
    @Test
    public void testCase() throws Exception {

        System.out.println("===直接序列化LocalDateTime,无论如何格式不可变化===");
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(JSON.toJSONString(dateTime));
        /*期望是 yyyy-MM-dd HH:mm:ss   实际是 LocalDateTime.toString() 的格式 */
        System.out.println(JSON.toJSONString(dateTime, SerializerFeature.WriteDateUseDateFormat));
        System.out.println(JSON.toJSONStringWithDateFormat(dateTime, "yyyy-MM-dd HH:mm:ss"));

        System.out.println("===直接序列化LocalDateTime.withNano(0),无论如何格式不可变化===");
        dateTime = LocalDateTime.now().withNano(0);
        System.out.println(JSON.toJSONString(dateTime));
        /*期望是 yyyy-MM-dd HH:mm:ss   实际是 LocalDateTime.toString() 的格式 */
        System.out.println(JSON.toJSONString(dateTime, SerializerFeature.WriteDateUseDateFormat));
        System.out.println(JSON.toJSONStringWithDateFormat(dateTime, "yyyy-MM-dd HH:mm:ss"));

        System.out.println("==序列化VO对象中包含LocalDateTime,无论如何格式不可变化==");
        LocalDateTimeModel localDateTimeModel = new LocalDateTimeModel();
        System.out.println(JSON.toJSONString(localDateTimeModel));
        /*期望是 yyyy-MM-dd HH:mm:ss   实际是 LocalDateTime.toString() 的格式 */
        System.out.println(JSON.toJSONString(localDateTimeModel, SerializerFeature.WriteDateUseDateFormat));
        System.out.println(JSON.toJSONStringWithDateFormat(localDateTimeModel, "yyyy-MM-dd HH:mm:ss"));

        System.out.println("==序列化VO对象中包含LocalDateTime.withNano(0),日期格式变化正确==");
        localDateTimeModel = new LocalDateTimeModel();
        localDateTimeModel.setStartDateTime(localDateTimeModel.getStartDateTime().withNano(0));
        localDateTimeModel.setEndDateTime(localDateTimeModel.getEndDateTime().withNano(0));
        System.out.println(JSON.toJSONString(localDateTimeModel));
        System.out.println(JSON.toJSONString(localDateTimeModel, SerializerFeature.WriteDateUseDateFormat));
        System.out.println(JSON.toJSONStringWithDateFormat(localDateTimeModel, "yyyy-MM-dd HH:mm:ss"));
    }

    class LocalDateTimeModel implements Serializable {

        private LocalDateTime startDateTime;

        private LocalDateTime endDateTime;

        private Date startDate;

        private Date endDate;

        public LocalDateTimeModel() {
            startDateTime = LocalDateTime.now();
            endDateTime = LocalDateTime.now().plusWeeks(1);

            startDate = new Date();
            endDate = new Date(startDate.getTime() + 36000000);
        }

        public LocalDateTime getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public LocalDateTime getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
