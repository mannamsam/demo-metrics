import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Test {
	public static void main(String args[]) throws Exception {
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		
		Set<ObjectName> objectNames = server.queryNames(null, null);
		
		for (ObjectName name : objectNames) {
		    MBeanInfo info = server.getMBeanInfo(name);
		    
		    System.out.println( " --------- " + name + " == " +  info.getClassName());
		}
		
		Hashtable<String,String> keyValues = new Hashtable<String,String>();
		keyValues.put("name", "Code Cache");
		//keyValues.put("type", "MemoryPool");
		
		
		ObjectName objectName = new ObjectName("java.lang", keyValues);
		
		objectNames = server.queryNames(objectName, null);
		
		for (ObjectName name : objectNames) {
		    MBeanInfo info = server.getMBeanInfo(name);
		    
		    System.out.println( " ( 1 ) --------- " + name.getDomain() + " " + name.getKeyPropertyList() );
		    for ( MBeanAttributeInfo attr :  info.getAttributes() ) {
		    		if ( attr.isWritable() ) { 
		    			System.out.println( " \t\t --------- " + attr.getName() + " -> "  + attr.getType() );
		    		}
		    }
		}
	}
}
