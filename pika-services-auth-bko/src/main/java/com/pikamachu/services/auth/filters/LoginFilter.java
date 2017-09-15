package com.pikamachu.services.auth.filters;

import javax.servlet.annotation.WebFilter;

import com.pikamachu.services.common.security.TokenAuthFilter;

/**
 * The type Login filter.
 */
@WebFilter({"/api/v1/me", "/api/v1/user/*"})
public class LoginFilter extends TokenAuthFilter {

}
