package com.example.powersync_java.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heshan Karunaratne
 */
@Data
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
}
