package net.cfrost.web.core.base.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import net.cfrost.web.core.base.exception.RetResultException;

@XmlRootElement
public class RetResult<T> {
    
    public final static int UNINIT = 0;
    public final static int SUCCESS = 1;
    public final static int UNKNOWN_ERR = -1;
    
    public RetResult() {
        this.dataSize = 0;
        this.returnFlag = RetResult.UNINIT;
        this.returnInfo = "UNINIT";
    }

    public RetResult(T data) {
        this.setData(data);
    }

    public RetResult(List<T> dataList) {
        this.setDataList(dataList);
    }

    private T data;
    
    private List<T> dataList;
    
    private Integer dataSize;
    
    private Integer returnFlag;
    
    private String returnInfo;
    
    private Date returnDate;
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        if(this.dataList != null){
            throw new RetResultException("RetResult has already been used as list data set");
        }
        this.data = data;
        this.dataSize = this.dataSize==null?0:1;
        if(this.returnInfo == "UNINIT") {
            this.returnInfo = null;
        }
    }
    
    public List<T> getDataList() {
        return dataList;
    }
    
    public void setDataList(List<T> dataList) {
        if(this.data != null){
            throw new RetResultException("RetResult has already been used as single data set");
        }
        this.dataList = dataList;
        this.dataSize = this.dataList==null?0:this.dataList.size();
        if(this.returnInfo == "UNINIT") {
            this.returnInfo = null;
        }
    }
    
    public Integer getReturnFlag() {
        return returnFlag;
    }
    
    public void setReturnFlag(Integer returnFlag) {
        this.returnFlag = returnFlag;
        
        if(this.returnInfo == null || this.returnInfo == "UNINIT") {
            switch(returnFlag) {
            case RetResult.SUCCESS : this.returnInfo = "SUCCESS";break;
            case RetResult.UNKNOWN_ERR : this.returnInfo = "UNKNOWN_ERR";break;
            }
        }
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public void setDataSize(Integer dataSize) {
    }

    public Integer getDataSize() {
        return this.dataSize;
    }

    public Date getReturnDate() {
        return returnDate == null?new Date():this.returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
