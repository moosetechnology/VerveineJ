package audi.client;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

/**
 * This is a simple standalone client application that invokes the
 * the <code>echoString</code> and <code>myoperation</code> operations of the
 * SimpleEjbService Web service.
 *
 * @author Copyright (c) @COPYRIGHT_CURRENTYEAR by BEA Systems, Inc. All Rights Reserved.
 */
public class Main {

	public static void main(String[] args) throws ServiceException,
			RemoteException {

		String url = args[0];
		String supplier = args[1];
		String partName = args[2];
		String partID = args[3];
		long amount = new Long(args[4]).longValue();
		String notes = args[5];

	    AudiSupplierService service = new AudiSupplierService_Impl();
	    MyPortType port = service.getHelloPort();

	    Stub stub = (Stub)port;
	    stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, args[0]);

	    OrderStruct in = new OrderStruct();

		in.setSupplier(supplier);
		in.setPartName(partName);
		in.setPartID(partID);
		in.setAmount(amount);
		in.setNotes(notes);

		OrderStruct orderResult = new OrderStruct();
		orderResult = port.processOrderApp(in);

        System.out.println("Order Application Response: " + orderResult.getNotes());
        System.out.println("Amount: " + orderResult.getAmount());
  }

}
