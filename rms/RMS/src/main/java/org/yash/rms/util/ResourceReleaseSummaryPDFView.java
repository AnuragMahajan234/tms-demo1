/* package org.yash.rms.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.yash.rms.dto.ResourceReleaseSummaryDTO;
import org.yash.rms.helper.ResourceReleaseSummaryHelper;

@Component(value = "resourceReleaseSummaryPDFView")
public class ResourceReleaseSummaryPDFView<E> extends AbstractView {

  
  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    List<ResourceReleaseSummaryDTO> resourcesReleaseSummaryDTOs = (List<ResourceReleaseSummaryDTO>) model.get("dataList");
    
    ResourceReleaseSummaryHelper.generatePDF(resourcesReleaseSummaryDTOs, response);
  }
}
*/