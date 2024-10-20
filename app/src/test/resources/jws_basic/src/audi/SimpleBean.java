package audi;

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
//import com.bea.wli.sb.examples.ExamplesText;
// Import the WebLogic-specific JWS annotation interface called WLHttpTransport

import weblogic.jws.WLHttpTransport;

//Import the bean representing a loan application summary

import audi.client.OrderStruct;

// EJBGen annotation that specifies that EJBGen should generate a stateless
// session EJB called Simple and its generated JAX-RPC Web service endpoint
// interface should be called "normal.SimpleBeanPortType".


@Session(ejbName="Simple",
         serviceEndpoint="audi.SimpleBeanPortType")

// Standard JWS annotation that specifies that the name of the Web Service is
// "myPortType", its public service name is "NormalLoanApprovalService", and the
// targetNamespace used in the generated WSDL is "http://example.org"

@WebService(name="myPortType",
            serviceName="AudiSupplierService",
            targetNamespace="http://example.org")

// Standard JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol.  In particular, it specifies that the SOAP messages
// are rpc-encoded.

@SOAPBinding(style=SOAPBinding.Style.RPC,
             use=SOAPBinding.Use.ENCODED)

// WebLogic-specific JWS annotation that specifies the port name is helloPort,
// and the context path and service URI used to build the URI of the Web
// Service is "njws_basic_ejb/NormalSimpleBean"

@WLHttpTransport(portName="helloPort",
                 contextPath="audi_jws_basic_ejb",
                 serviceUri="AudiSupplierSimpleBean")

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

  public OrderStruct orderResponse(OrderStruct orderRequest) throws RemoteException {
    //System.out.println("======= Normal Loan Application Processor");
    //System.out.println("======= Loan Application received for " + loanRequest.getName() );
    //System.out.println("======= ID             : "   + loanRequest.getSSN());
    //System.out.println("======= Amount         : "   + loanRequest.getAmount());
    //System.out.println("======= Rate           : "   + loanRequest.getRate());
    //System.out.println("======= # Years        : "   + loanRequest.getNumOfYear());
    //System.out.println("======= Notes          : "   + loanRequest.getNotes());
    //System.out.println("=====================================================");

    //Loan approved
	  orderRequest.setNotes("ORDER EXECUTED by Audi`");
    //  loanRequest.setNotes(ExamplesText.EXAMPLES_LABEL_NOTES.get());
    //System.out.println("======= " + "Loan Request Status: " + loanRequest.getNotes());
    //System.out.println("=====================================================");
    return orderRequest;

  }


  // Standard EJB methods.  Typically there's no need to override the methods.

  public void ejbCreate() {}
  public void ejbActivate() {}
  public void ejbRemove() {}
  public void ejbPassivate() {}
  public void setSessionContext(SessionContext sc) {}
}
