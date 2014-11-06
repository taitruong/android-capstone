package org.aliensource.symptommanagement.cloud.controller;

import org.aliensource.symptommanagement.cloud.auth.ClientAndUserDetailsService;
import org.aliensource.symptommanagement.cloud.service.SecurityService;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ttruong on 05-Nov-14.
 */
@Controller
public class SecurityController {

    @Autowired
    @Qualifier("clientDetailsService") // userDetailsService and clientDetailsService are the same
    private ClientAndUserDetailsService combinedService;

    @RequestMapping(value = ServiceUtils.PATH_AUTH_SERVICE, method = RequestMethod.POST)
    public @ResponseBody boolean hasRole(@RequestParam(ServiceUtils.PARAMETER_ROLE) String role, Principal p, HttpServletRequest request) {
        UserDetails userDetails = combinedService.loadUserByUsername(p.getName());

        if (userDetails != null) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            if (authorities != null) {
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals(role)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
