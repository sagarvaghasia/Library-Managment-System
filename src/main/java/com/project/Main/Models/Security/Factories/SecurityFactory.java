package com.project.Main.Models.Security.Factories;

import com.project.Main.Models.Security.ISecurity;
import com.project.Main.Models.Security.Security;

public class SecurityFactory implements ISecurityFactory {

    private static ISecurityFactory securityFactory = null;

    private SecurityFactory() {
    }

    public static ISecurityFactory instance() {
        if (securityFactory == null) {
            return new SecurityFactory();
        }
        return securityFactory;
    }

    @Override
    public ISecurity createSecurity() {
        return new Security();
    }
}
