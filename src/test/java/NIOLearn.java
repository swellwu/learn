import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/12/15.
 */
public class NIOLearn {

    private String  path=Thread.currentThread().getContextClassLoader().getResource("filename.txt").getPath();

    @Before
    public void getPath(){

    }

    //基本的channel学习,FileChannel,与ByteBuffer配合，ByteBuffer可以输入也可以输出，
    // flip切换为读模式（将position置0，limit置为position）
    // 清空缓冲区，clear、compact，clear清空整个缓冲区，compact清空已读缓冲区
    @Test
    public void channelTest1() throws IOException {
        RandomAccessFile file=new RandomAccessFile(path,"rw");
        FileChannel inChannel=file.getChannel();
        ByteBuffer buf=ByteBuffer.allocate(1024*10);

        int bytesRead;
        while((bytesRead=inChannel.read(buf))!=-1){
            System.out.println("Read "+bytesRead);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            buf.clear();
        }
        file.close();
    }

}
