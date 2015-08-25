package com.zlhw.client.base.event
{
    import com.zlhw.client.base.model.BaseModel;
    
    import flash.events.*;

    public class OperateRendererEvent extends Event
    {
        public var obj:BaseModel;

        public function OperateRendererEvent(param1:String)
        {
            super(param1, true, true);
            return;
        }// end function

    }
}
