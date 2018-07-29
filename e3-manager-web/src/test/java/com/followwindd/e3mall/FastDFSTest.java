package com.followwindd.e3mall;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class FastDFSTest {
    @Test
    public void test1() throws IOException, MyException {
        ClientGlobal.init("D:\\workspace\\e3parent\\e3-manager-web\\src\\main\\resources\\conf\\fastDFS.properties");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String[] strings = storageClient.upload_file("C:\\Users\\followWindD\\Desktop\\1.png", "png", null);
        System.out.println(Arrays.toString(strings));
    }
}
