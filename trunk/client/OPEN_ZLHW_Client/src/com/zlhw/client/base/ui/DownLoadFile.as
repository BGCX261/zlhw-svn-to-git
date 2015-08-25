package com.zlhw.client.base.ui
{
    import flash.events.*;
    import flash.net.*;
    import mx.controls.*;

    public class DownLoadFile 
    {
        private var fileRef:FileReference;
        public var FILE_URL:String;
        private var urlReq:URLRequest;

        public function DownLoadFile()
        {
            this.urlReq = new URLRequest(this.FILE_URL);
            this.fileRef = new FileReference();
            this.fileRef.addEventListener(Event.CANCEL, this.doEvent);
            this.fileRef.addEventListener(Event.COMPLETE, this.onSuccess);
            this.fileRef.addEventListener(Event.OPEN, this.doEvent);
            this.fileRef.addEventListener(Event.SELECT, this.onSelected);
            this.fileRef.addEventListener(HTTPStatusEvent.HTTP_STATUS, this.doEvent);
            this.fileRef.addEventListener(IOErrorEvent.IO_ERROR, this.onDownloadError);
            this.fileRef.addEventListener(ProgressEvent.PROGRESS, this.doEvent);
            this.fileRef.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.doEvent);
            return;
        }// end function

        public function download(PROGRESS:String) : void
        {
            this.urlReq.url = PROGRESS;
            this.fileRef.download(this.urlReq);
            return;
        }// end function

        protected function onDownloadError(event:IOErrorEvent) : void
        {
            Alert.show("下载出错", "提示");
            return;
        }// end function

        private function onSuccess(event:Event) : void
        {
            Alert.show("成功下载", "提示");
            return;
        }// end function

        private function doEvent(event:Event) : void
        {
            return;
        }// end function

        private function onSelected(event:Event) : void
        {
            return;
        }// end function

    }
}
