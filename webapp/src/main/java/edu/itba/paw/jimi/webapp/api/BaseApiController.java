package edu.itba.paw.jimi.webapp.api;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public abstract class BaseApiController {
	
	URI buildBaseURI(UriInfo uriInfo) {
		return URI.create(String.valueOf(uriInfo.getBaseUri()) +
				UriBuilder.fromResource(this.getClass()).build() +
				"/");
	}
}
