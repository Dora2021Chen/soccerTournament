package com.api.soccerTournament.model.response;

import com.api.soccerTournament.model.Entity;

import java.util.ArrayList;

public class Response<T extends Entity> {
    public int statusCode;  //0:succeed, 1000:fail
    public String statusMsg;
    public ArrayList<T> entities;

    public Response() {
        this.statusCode = Const.STATUS_CODE_SUCCEED;
        this.statusMsg = Const.statusMap.get(this.statusCode);
        this.entities = new ArrayList<>();
    }

    public Response(int statusCode) {
        this.statusCode = statusCode;
        this.statusMsg = Const.statusMap.get(this.statusCode);
        this.entities = new ArrayList<>();
    }

    public Response(int statusCode, ArrayList<T> entities) {
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
        this.entities = new ArrayList<>();
    }

    public Response(int statusCode, String statusMsg, ArrayList<T> entities) {
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

    public Entity getEntity() {
        if ((entities == null) || (entities.size() == 0)) {
            return null;
        }

        return entities.get(0);
    }
}
