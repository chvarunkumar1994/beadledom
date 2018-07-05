package com.cerner.beadledom.health.api;

import com.cerner.beadledom.health.dto.HealthDto;
import com.cerner.beadledom.health.dto.HealthJsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

@Api(value = "/health",
    description = "Health and dependency checks")
@Path("meta/availability")
public interface AvailabilityResource {
  @GET
  @Produces(MediaType.TEXT_HTML)
  StreamingOutput getBasicAvailabilityCheckHtml();

  @ApiOperation(value = "Basic Availability Check",
      notes = "Always returns 200. The JSON will only include the message field.",
      response = HealthDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 503, message = "unhealthy"),
      @ApiResponse(code = 200, message = "healthy")})
  @Operation(summary = "Basic Availability Check",
      description = "Always returns 200. The JSON will only include the message field.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              description = "healthy",
              content = @Content(schema = @Schema(implementation = HealthDto.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "503",
              description = "unhealthy"
          )
      }
  )
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @JsonView(HealthJsonViews.Availability.class)
  HealthDto getBasicAvailabilityCheck();
}
