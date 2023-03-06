import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Page;
import fileio.ActionInput;
import fileio.Input;

import java.io.File;
import java.io.IOException;

public class Main {

    /** OOP TV Project */
    public static void main(final String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = objectMapper.createArrayNode();
        Page pageData = new Page(inputData);

        for (ActionInput act : inputData.getActions()) {
            switch (act.getType()) {
                case "change page" -> pageData.changePage(act, output);
                case "on page" -> pageData.onPage(act, output);
                case "back" -> pageData.back(act, output);
                case "database" -> pageData.database(act, output);
                default -> System.out.println("Error");
            }
        }

        if (pageData.getCurrentUser() != null
                && pageData.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            pageData.recommendations(output);
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
