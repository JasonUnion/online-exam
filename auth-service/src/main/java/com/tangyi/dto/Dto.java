package com.tangyi.dto;

/**Dto
 * Created by tangyi on 2017-04-10.
 */
public class Dto {

    public Dto(String message) {
        this.message = message;
    }

    /*提示内容*/
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
