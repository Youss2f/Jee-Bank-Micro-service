package net.youssfi.customerservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "all", types = Customer.class)
public interface CustomerProjection {
    public String getName();
    public String getEmail();
}
