package org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Implementations;

import com.android.volley.VolleyError;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.BaseEventBusEvent;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Interfaces.IRequestEventBusEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class HTTPRequestCompleteEvent extends BaseEventBusEvent implements IRequestEventBusEvent {

    private String result;
    private VolleyError error;
    private List<Object> optionalData;

    public HTTPRequestCompleteEvent(){
        this.optionalData = new ArrayList<>();
    }


    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public void setResult(String result) {
        this.result = result;
    }

    public boolean hasError(){
        return this.error !=null;
    }

    @Override
    public void addOptionalData(Object object) {
        this.optionalData.add(object);
    }

    @Override
    public List<Object> getOptionalData() {
        return this.optionalData;
    }

    @Override
    public Object getDataAtIndex(int i) {
        return this.optionalData.get(i);
    }

    @Override
    public void setError(VolleyError error) {
        this.error = error;
    }

}
