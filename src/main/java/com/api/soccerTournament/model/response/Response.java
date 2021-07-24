package com.api.soccerTournament.model.response;

import com.api.soccerTournament.model.Entity;

import java.util.ArrayList;

public class Response {
    public int statusCode;  //0:succeed, 1000:fail
    public String statusMsg;
    public ArrayList<Entity> entities;

    public Response() {
        this.statusCode = Const.statusCodeSucceed;
        this.statusMsg = Const.statusMap.get(this.statusCode);
        this.entities = new ArrayList<>();
    }

    public Response(int statusCode) {
        this.statusCode = statusCode;
        this.statusMsg = Const.statusMap.get(this.statusCode);
        this.entities = new ArrayList<>();
    }

    public Response(int statusCode, ArrayList<Entity> entities) {
        this.statusCode = statusCode;
        this.statusMsg = Const.statusMap.get(this.statusCode);
        this.entities = entities;
    }

    public Response(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        if ((statusMsg == null) || (statusMsg.length() == 0)) {
            this.statusMsg = statusMsg;
        } else {
            StringBuilder statusMsgBuilder = new StringBuilder();
            statusMsgBuilder.append(Const.statusMap.get(this.statusCode));
            statusMsgBuilder.append(": ").append(statusMsg);
            this.statusMsg = statusMsgBuilder.toString();
        }
    }

    public Response(int statusCode, String statusMsg, ArrayList<Entity> entities) {
        this.statusCode = statusCode;
        this.entities = entities;
        if ((statusMsg == null) || (statusMsg.length() == 0)) {
            this.statusMsg = statusMsg;
        } else {
            StringBuilder statusMsgBuilder = new StringBuilder();
            statusMsgBuilder.append(Const.statusMap.get(this.statusCode));
            statusMsgBuilder.append(":").append(statusMsg);
            this.statusMsg = statusMsgBuilder.toString();
        }
    }
}
