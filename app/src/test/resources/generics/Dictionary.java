// No package declaration for testing purposes

import java.util.Collection;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;

import org.hamcrest.core.IsInstanceOf;

public class Dictionary<B> {

	protected Map<B,NamedEntity> mapBind;

	protected Map<String,Collection<NamedEntity>> mapName;

	protected Map<NamedEntity,ImplicitVars> mapImpVar;

	protected class ImplicitVars {
		public ImplicitVariable self_iv;
		public ImplicitVariable super_iv;
	}

	/* method added to test a specific bug with constructor declaration having a null binding */
	public Dictionary(T arg) {
		System.out.println(arg + " is not used");
	}
	
	public Dictionary(Repository famixRepo) {
		this.famixRepo = famixRepo;
		
		this.mapBind = new Hashtable<B,NamedEntity>();
		this.mapName = new Hashtable<String,Collection<NamedEntity>>();
		this.mapImpVar = new Hashtable<NamedEntity,ImplicitVars>();
		
		if (! this.mapBind.isEmpty()) {
			// nothing
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends NamedEntity> Collection<T> getEntityByName(Class<T> fmxClass, String name) {
		Collection<T> ret = new ArrayList<T>();
		Collection<NamedEntity> l_name = mapName.get(name);
		
		if (l_name != null ) {
			for (NamedEntity obj : l_name) {
				if (fmxClass.isInstance(obj)) {
					ret.add((T) obj);
				}
				fmxClass.class.isInstance(null);  /// for testing VerveineJ
			}
		}
		return ret;
	}

	public NamedEntity getEntityByBinding(B bnd) {
		if (bnd == null) {
			return null;
		}
		else {
			return mapBind.get(bnd);
		}
	}
	
	public void parameterClass(ArrayList<B> list) {
		
	}
	protected void testMethodRettype() {
		NamedEntity ent = createFamixEntity(NamedEntity.class, "test");
		mapBind.uniplementedMethod(ent, "2nd param");
	}

	protected <T extends NamedEntity> T createFamixEntity(Class<T> fmxClass, String name) {
		T fmx = null;

		try {
			fmx = fmxClass.newInstance();
		} catch (Exception e) {
			System.err.println("Unexpected error, could not create a FAMIX entity: "+e.getMessage());
			e.printStackTrace();
		}
		
		if (fmx != null) {
			fmx.setName(name);
			fmx.setIsStub(Boolean.TRUE);

			mapEntityToName(name, fmx);
			
			// put new entity in Famix repository
			this.famixRepo.add(fmx);
		}

		return fmx;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends NamedEntity> T ensureFamixEntity(Class<T> fmxClass, B bnd, String name) {
		T fmx = null;
		
		if (ImplicitVariable.class.isAssignableFrom(fmxClass)) {
			return null;
		}

		if (fmx != null) {
			return fmx;
		}

		fmx = createFamixEntity(fmxClass, name);
		// put new entity in mappers
		if (bnd != null) {
			mapBind.put(bnd, fmx);
		}
		
		return fmx;
	}
	
}

class NamedEntity {
	public boolean isInstance(NamedEntity obj) {
		return false;
	}
	public void setName(String f) {
	}
	public void setIsStub(Boolean f) {
	}
}

class ImplicitVariable extends NamedEntity {

}

class Repository {
	protected Dictionary<String> dico;
	public boolean isEmtpy() {
		return dico == null;
	}
	public void add(NamedEntity f) {
	}
}
