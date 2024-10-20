package generics;

import java.util.ArrayList;
import java.util.List;

public class WrongInvocation
{
	public List<X> getX()
	{
		return new ArrayList<ABC>();
	}
}

interface X {}

interface ABC extends X {}