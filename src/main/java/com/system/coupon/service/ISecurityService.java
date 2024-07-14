package com.system.coupon.service;

import org.json.JSONObject;

public interface ISecurityService {
    String encryptData(Object data);
    JSONObject decryptData(String encryptedString);

}
