package com.cerner.beadledom.client;

import com.cerner.beadledom.client.resteasy.BeadledomResteasyClientBuilder;
import com.cerner.beadledom.jaxrs.GenericResponse;
import com.cerner.beadledom.jaxrs.GenericResponses;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Implementation of the test Resource API.
 *
 * @author John Leacox
 */
public class TestResourceImpl implements TestResource {
  @Context
  HttpHeaders httpHeaders;

  @Inject
  TestResourceImpl(HttpHeaders httpHeaders) {
    this.httpHeaders = httpHeaders;
  }

  @Override
  public String get() {
    return "hello world";
  }

  @Override
  public String error() {
    throw new WebApplicationException(404);
  }

  @Override
  public String getData(String data) {
    return "Here is your string:" + data;
  }

  @Override
  public String loopyGetCorrelationId() {
    BeadledomClient client = BeadledomResteasyClientBuilder.newClient();
    BeadledomWebTarget target = client.target("http://localhost:9091/faux-service");
    TestResource proxy = target.proxy(TestResource.class);

    return proxy.echoCorrelationId();
  }

  @Override
  public String echoCorrelationId() {
    return httpHeaders.getHeaderString("Correlation-Id");
  }

  @Override
  public JsonModel getJson() {
    JsonModel jsonModel = new JsonModel();
    jsonModel.setFieldOne("one");
    jsonModel.setFieldTwo("two");
    return jsonModel;
  }

  @Override
  public Response getResponseJson() {
    return Response.ok(getJson()).build();
  }

  @Override
  public GenericResponse<JsonModel> getGenericResponseJson() {
    return GenericResponses.ok(getJson()).build();
  }

  @Override
  public Response getResponseError() {
    throw new WebApplicationException("An Error Occurred", 400);
  }

  @Override
  public GenericResponse<JsonModel> getGenericResponseError() {
    throw new WebApplicationException("An Error Occurred", 400);
  }

  @Override
  public GenericResponse<String> getGenericResponseJsonError() {
    return GenericResponses.<String>serverError().errorEntity(getJson()).build();
  }
}
