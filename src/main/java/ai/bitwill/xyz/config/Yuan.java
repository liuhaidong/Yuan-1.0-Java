package ai.bitwill.xyz.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The main class for a user to interface with the Inspur Yuan API.
 *     A user can set account info and add examples of the API request.
 */
@Configuration
@Data
public class Yuan {
    @Value("${engine}")
    private String engine;

    @Value("${temperature}")
    private float temperature;

    @Value("${max_tokens}")
    private int max_tokens;

    @Value("${input_prefix}")
    private String input_prefix;

    @Value("${input_suffix}")
    private String input_suffix;

    @Value("${output_prefix}")
    private String output_prefix;

    @Value("${output_suffix}")
    private String output_suffix;

    @Value("${append_output_prefix_to_query}")
    private boolean append_output_prefix_to_query;

    @Value("${topK}")
    private int topK;

    @Value("${topP}")
    private float topP;

    @Value("${SUBMIT_URL}")
    private String SubmitUrl;

    @Value("${REPLY_URL}")
    private String ReplyUrl;

    @Value(("${dialog_sample}"))
    private String dialogSample ;

    private long waitingTime = 5000l;
}
