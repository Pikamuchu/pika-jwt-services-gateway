package com.pikamachu.services.gateway.filters;

import javax.servlet.annotation.WebFilter;

import com.pikamachu.services.common.security.TokenAuthFilter;

/**
 * The type Auth filter.
 */
@WebFilter("/api/v1/*")
public class AuthFilter extends TokenAuthFilter {

}
