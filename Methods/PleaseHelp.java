import java.util.*;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import java.util.Arrays;

public class PleaseHelp {
    AsciiTable table = new AsciiTable();
    table.addRule();
    table.addRow("Admin Name", "Admin Exists", "Admin ID", "Admin Pin");
    table.addRule();

    for(int i = 0; i < adminName.length; i++) {
        if(adminExists[i]) {
            table.addRow(adminName[i], adminExists[i], 
                        adminCredentials[i][0], adminCredentials[i][1]);
        }
    }
    table.addRule();
    System.out.println(table.render());
}
