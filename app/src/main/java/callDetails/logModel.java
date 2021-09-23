package callDetails;

import java.util.Date;

public class logModel {
    String callDate ;
    String callDayTime;
    String callDuration ;
    String  phNumber ,callType  ,name ;

    public logModel() {
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDayTime() {
        return callDayTime;
    }

    public void setCallDayTime(String callDayTime) {
        this.callDayTime = callDayTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public logModel(String callDate, String callDayTime, String callDuration, String phNumber, String callType, String name) {
        this.callDate = callDate;
        this.callDayTime = callDayTime;
        this.callDuration = callDuration;
        this.phNumber = phNumber;
        this.callType = callType;
        this.name = name;
    }

    @Override
    public String toString() {
        return "logModel{" +
                "callDate='" + callDate + '\'' +
                ", callDayTime='" + callDayTime + '\'' +
                ", callDuration='" + callDuration + '\'' +
                ", phNumber='" + phNumber + '\'' +
                ", callType='" + callType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
