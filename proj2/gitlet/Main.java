package gitlet;

import java.io.File;
import java.util.Date;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
      /* if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }*/
        /*
         * If the debug script is not working
         * Try comment out the first if statement above(if args. length == 0....)
         * and initial the firstArg manually to test a specific command
         * eg:to test init,manually assign firstArg "init" and then debug
         * (something that looks like: String firstArg = "init";)
         */
        //String firstArg = args[0];
        String firstArg = "init";
        File test = Utils.join(Repository.CWD, "Test.txt");
        String string = Utils.sha1(test);
        System.out.println(string);
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                String FileName = args[1];
                Repository.add(FileName);
                break;
            // TODO: FILL THE REST IN
            default:
                System.out.println("No command with that name exists.");
        }
    }
}
