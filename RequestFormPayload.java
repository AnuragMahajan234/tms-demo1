package demo;

import java.util.List;
import java.util.Map;

public class RequestFormPayload {

	private Map<String, List<DropdownRequestTms>> listRequestPayload;
	private Map<String, List<String>> enumsRequestPayload;

	public Map<String, List<DropdownRequestTms>> getListRequestPayload() {
		return listRequestPayload;
	}

	public void setListRequestPayload(Map<String, List<DropdownRequestTms>> listRequestPayload) {
		this.listRequestPayload = listRequestPayload;
	}

	public Map<String, List<String>> getEnumsRequestPayload() {
		return enumsRequestPayload;
	}

	public void setEnumsRequestPayload(Map<String, List<String>> enumsRequestPayload) {
		this.enumsRequestPayload = enumsRequestPayload;
	}

}
