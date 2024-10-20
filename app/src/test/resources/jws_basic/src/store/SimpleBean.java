package store;

import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

// Import the standard JWS annotation interfaces

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;

// Import the EJBGen annotation interfaces

import weblogic.ejbgen.*;

// Import the WebLogic-specific JWS annotation interface called WLHttpTransport

import weblogic.jws.WLHttpTransport;

//Import the bean representing a loan application summary

import store.client.OrderStruct;

// EJBGen annotation that specifies that EJBGen should generate a stateless
// session EJB called Simple and its generated JAX-RPC Web service endpoint
// interface should be called "credit.SimpleBeanPortType".


@Session(ejbName="Simple",
         serviceEndpoint="store.SimpleBeanPortType")

// Standard JWS annotation that specifies that the name of the Web Service is
// "myPortType", its public service name is "creditLoanApprovalService", and the
// targetNamespace used in the generated WSDL is "http://example.org"

@WebService(name="myPortType",
            serviceName="orderService",
            targetNamespace="http://example.org")

// Standard JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol.  In particular, it specifies that the SOAP messages
// are rpc-encoded.

@SOAPBinding(style=SOAPBinding.Style.RPC,
             use=SOAPBinding.Use.ENCODED)

// WebLogic-specific JWS annotation that specifies the port name is helloPort,
// and the context path and service URI used to build the URI of the Web
// Service is "njws_basic_ejb/creditSimpleBean"

@WLHttpTransport(portName="helloPort",
                 contextPath="store_basic_ejb",
                 serviceUri="orderSimpleBean")

/**
 * This JWS file forms the basis of a stateless-session EJB implemented
 * WebLogic Web Service.
 *
 *
 * Copyright (c) 2005 by BEA Systems, Inc. All Rights Reserved.
 */

public class SimpleBean implements SessionBean {

   /**
   * Standard JWS annotation to expose method as an operation named
   * "processLoanApp".
   */
  @WebMethod(operationName="processOrderApp")

  public String orderResponse(OrderStruct orderRequest) throws RemoteException {
    //System.out.println("======= Credit Rating Service Processor");
    //System.out.println("======= Credit Rating Request received for " + loanRequest.getName() );
    //System.out.println("======= ID             : "   + loanRequest.getSSN());
    //System.out.println("======= Amount         : "   + loanRequest.getAmount());
    //System.out.println("======= Rate           : "   + loanRequest.getRate());
    //System.out.println("======= # Years        : "   + loanRequest.getNumOfYear());
    //System.out.println("======= Notes          : "   + loanRequest.getNotes());
    //System.out.println("=====================================================");

   String orderRating = "AA";
    //loanRequest.setNotes("APPROVED BY THE <i><b>credit</b></i> LOAN APPLICATION PROCESSING SERVICE");
    //System.out.println("======= " + "Credit Rating : " + creditRating);
    //System.out.println("=====================================================");
    return orderRating;

  }


  // Standard EJB methods.  Typically there's no need to override the methods.

  public void ejbCreate() {}
  public void ejbActivate() {}
  public void ejbRemove() {}
  public void ejbPassivate() {}
  public void setSessionContext(SessionContext sc) {}
}
