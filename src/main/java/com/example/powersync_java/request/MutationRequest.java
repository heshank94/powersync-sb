package com.example.powersync_java.request;

import lombok.*;

import java.util.Map;

/**
 * @author Heshan Karunaratne
 */
@Data
@Getter
@Setter
public class MutationRequest {
    private String table;
    private Map<String, Object> data;
}
