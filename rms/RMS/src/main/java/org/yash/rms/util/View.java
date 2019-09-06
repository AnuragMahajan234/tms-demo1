package org.yash.rms.util;

public class View {
	
	    public static class Public {
	    }
	 
	    public static class Internal extends Public {
	    }
	    
	    public static class MyJSONVIEW extends Public {
	    }
	    
	    public static class MyJSONVIEW1 extends MyJSONVIEW {
	    }
	    
	    public static class SampleView extends Public {
	    }

	    
	    public static class CommonView{}
	    public static class ListView extends CommonView{} //to have limited fields
	    public static class DetailView extends ListView{} //to have more fields on UI
	 
	}

