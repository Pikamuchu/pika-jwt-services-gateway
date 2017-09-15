package com.pikamachu.services.auth.filters;

import javax.servlet.annotation.WebFilter;

import com.pikamachu.services.common.security.TokenAuthFilter;

/**
 * The type Auth filter.
 */
@WebFilter("/api/v1/me")
public class AuthFilter extends TokenAuthFilter {

}
