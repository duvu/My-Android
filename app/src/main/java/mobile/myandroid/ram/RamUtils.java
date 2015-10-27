package mobile.myandroid.ram;

import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * My Android
 * mobile.myandroid.ram
 * Created by beou on 27/10/2015.
 */
public class RamUtils {
    private static final String TAG = "RamUtils";
    private static final String MEM_TOTAL           = "MemTotal";
    private static final String MEM_FREE            = "MemFree";
    private static final String BUFFERS             = "Buffers";
    private static final String CACHED              = "Cached";
    private static final String SWAP_FREE           = "SwapFree";
    private static final String SWAP_TOTAL          = "SwapTotal";

    /**
         MemTotal
         MemFree
         Buffers
         Cached
         SwapCached
         Active
         Inactive
         Active(anon)
         Inactive(anon)
         Active(file)
         Inactive(file)
         Unevictable
         Mlocked
         HighTotal
         HighFree
         LowTotal
         LowFree
         SwapTotal
         SwapFree
         Dirty
         Writeback
         AnonPages
         Mapped
         Shmem
         Slab
         SReclaimable
         SUnreclaim
         KernelStack
         PageTables
         NFS_Unstable
         Bounce
         WritebackTmp
         CommitLimit
         Committed_AS
         VmallocTotal
         VmallocUsed
         VmallocChunk
     */

    public static long getRAMTotal() {
        MemoryInfo ms = getMemorySize();
        return ms.total;
    }
    public static long getRAMFree() {
        MemoryInfo ms = getMemorySize();
        return ms.free;
    }

    private static MemoryInfo getMemorySize() {
        final Pattern PATTERN = Pattern.compile("([a-zA-Z]+):\\s*(\\d+)");

        MemoryInfo result = new MemoryInfo();
        String line;
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            while ((line = reader.readLine()) != null) {
                Log.i(TAG, line);
                Matcher m = PATTERN.matcher(line);
                if (m.find()) {
                    String name = m.group(1);
                    String size = m.group(2);

                    if (name.equalsIgnoreCase(MEM_TOTAL)) {
                        result.total = Long.parseLong(size);
                    } else if (name.equalsIgnoreCase(MEM_FREE)){
                        result.free = Long.parseLong(size);
                    } else if( name.equalsIgnoreCase(BUFFERS)){
                        result.buffer = Long.parseLong(size);
                    } else if (name.equalsIgnoreCase(CACHED)){
                        result.cached = Long.parseLong(size);
                    } else if (name.equalsIgnoreCase(SWAP_FREE)) {
                        result.freeSwap = Long.parseLong(size);
                    } else if (name.equalsIgnoreCase(SWAP_TOTAL)) {
                        result.totalSwap = Long.parseLong(size);
                    }
                }
            }
            reader.close();

            result.total *= 1024;
            result.free *= 1024;
            result.buffer *= 1024;
            result.cached *= 1024;
            result.freeSwap *= 1024;
            result.totalSwap *= 1024;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class MemoryInfo {
        public long total = 0;
        public long free = 0;
        public long buffer = 0;
        public long cached = 0;
        public long totalSwap = 0;
        public long freeSwap = 0;
    }
}
