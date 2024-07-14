package com.system.coupon.security;

import com.system.coupon.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import lombok.RequiredArgsConstructor;
import java.lang.reflect.Parameter;
import org.json.JSONObject;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DecryptRequestBodyAspect {
    private final SecurityService security;

    @Around("@annotation(DecryptRequestBody)")
    public Object decryptRequestBody(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType() == String.class && args[i] != null) {
                String requestBody = (String) args[i];
                JSONObject requestJson = new JSONObject(requestBody);

                if (requestJson.has("encryptedData")) {
                    String encryptedData = requestJson.getString("encryptedData");
                    JSONObject decryptedJson = security.decryptData(encryptedData);
                    args[i] = decryptedJson.toString();
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
