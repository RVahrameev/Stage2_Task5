package vtb.courses.stage2_task5.Service;

import vtb.courses.stage2_task5.Request.CreateCsiRequest;
import vtb.courses.stage2_task5.Response.CsiResponse;

public interface CsiServiceIntf {
    CsiResponse createCsi(CreateCsiRequest csiRequest);
}
