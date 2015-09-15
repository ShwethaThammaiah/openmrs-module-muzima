/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.muzima.web.utils;

import net.minidev.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.model.DataSource;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.model.ErrorMessage;
import org.openmrs.module.muzima.model.QueueData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class WebConverter {

    public static Map<String, Object> convertDataSource(final DataSource dataSource) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (dataSource != null) {
            map.put("uuid", dataSource.getUuid());
            map.put("name", dataSource.getName());
            map.put("description", dataSource.getDescription());
            map.put("created", Context.getDateFormat().format(dataSource.getDateCreated()));
        }
        return map;
    }

    public static Map<String, Object> convertQueueData(final QueueData queueData) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (queueData != null) {
            map.put("uuid", queueData.getUuid());
            map.put("discriminator", queueData.getDiscriminator());
            map.put("source", queueData.getDataSource().getName());
            map.put("payload", queueData.getPayload());
            map.put("submitted", Context.getDateFormat().format(queueData.getDateCreated()));
            map.put("locationId", queueData.getLocation().getLocationId());
            map.put("locationName", queueData.getLocation().toString());
            map.put("providerId", queueData.getProvider().getSystemId());
            map.put("providerName", queueData.getProvider().getGivenName());
            map.put("formName", queueData.getFormName());
        }
        return map;
    }

    public static Map<String, Object> convertErrorData(final ErrorData errorData) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (errorData != null) {
            map.put("uuid", errorData.getUuid());
            map.put("discriminator", errorData.getDiscriminator());
            map.put("source", errorData.getDataSource().getName());
            map.put("message", errorData.getMessage());
            map.put("payload", errorData.getPayload());
            map.put("locationId", errorData.getLocation().getLocationId());
            map.put("locationName", errorData.getLocation().toString());
            map.put("providerId", errorData.getProvider().getSystemId());
            map.put("providerName", errorData.getProvider().getGivenName());
            map.put("formName", errorData.getFormName());
            map.put("submitted", Context.getDateFormat().format(errorData.getDateCreated()));
            map.put("processed", Context.getDateFormat().format(errorData.getDateProcessed()));

            Map<String, Object> errorMap = new HashMap<String, Object>();
            for(Object e : errorData.getErrorMessages()){
                ErrorMessage errorMessage = (ErrorMessage)e;
                errorMap.put(errorMessage.getId().toString(), errorMessage.getMessage());
            }

            map.put("Errors", JSONObject.toJSONString(errorMap));
        }
        return map;
    }



    public static Map<String, Object> convertEditRegistrationData(final ErrorData errorData) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (errorData != null) {
            map.put("uuid", errorData.getUuid());
            map.put("discriminator", errorData.getDiscriminator());
            //map.put("form-template-name",errorData.)
            map.put("source", errorData.getDataSource().getName());
            map.put("message", errorData.getMessage());
            XmlJsonUtil.createPatientValuesFromPayload(map, errorData.getPayload());
            map.put("submitted", Context.getDateFormat().format(errorData.getDateCreated()));
            map.put("processed", Context.getDateFormat().format(errorData.getDateProcessed()));


            System.out.println("data" + map.toString());

        }
        return map;
    }

    public static  Map<String,Object> convertErrorMessages(List<ErrorMessage> errorMessages) {

        Map<String,Object> outerMap = new HashMap<String, Object>();
        Map<String, Object> innerMap = new HashMap<String, Object>();
        int count =0;
        for (ErrorMessage errorMessage : errorMessages) {
            count++;
            innerMap.put(new Integer(count).toString(), errorMessage.getMessage());
        }
        outerMap.put("Errors",innerMap);
        return outerMap;
    }
}
