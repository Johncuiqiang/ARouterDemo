package arouter.cuiqiang.com.baselib.utils.file;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by cuiqiang on 2018/7/20..
 */

public class CmdShellUtil {

    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    private CmdShellUtil(){
        throw new AssertionError();
    }

    public static boolean checkRootPermission(){
        return execCommand("echo root",true,false).result == 0;
    }

    public static CommandResult execCommand(String command, boolean isRoot){
        return execCommand(new String[] {command},isRoot,true);
    }

    public static CommandResult execCommand(List<String> commands, boolean isRoot){
        return execCommand(commands == null ? null : commands.toArray(new String[] {}),isRoot,true);
    }

    public static CommandResult execCommand(String[] commands, boolean isRoot){
        return execCommand(commands,isRoot,true);
    }

    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg){
        return execCommand(new String[] {command},isRoot,isNeedResultMsg);
    }

    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg){
        return execCommand(commands == null? null : commands.toArray(new String[] {}),isRoot,isNeedResultMsg);
    }

    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg){
        int result = -1;
        if (commands == null || commands.length == 0){
            return new CommandResult(result,null,null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands){
                if (command == null){
                    continue;
                }
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            if (isNeedResultMsg){
                successMsg  = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null){
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null){
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (process != null) {
                    InputStream errorStream = process.getErrorStream();
                    Log.v("COMMAND", IOUtil.streamToString(errorStream));
                }

                if (os != null){
                    os.close();
                }
                if (successResult != null){
                    successResult.close();
                }
                if (errorResult != null){
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null){
                process.destroy();
            }
        }
        return new CommandResult(result,successMsg == null ? null : successResult.toString(),errorMsg == null ? null : errorResult.toString());
    }

    public static class CommandResult{
        public int result;
        public String successMsg;
        public String errorMsg;
        public CommandResult(int result){
            this.result = result;
        }
        public CommandResult(int result, String successMsg, String errorMsg){
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }


}
