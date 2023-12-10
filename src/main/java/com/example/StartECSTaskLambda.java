import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.RunTaskRequest;
import com.amazonaws.services.ecs.model.RunTaskResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class StartECSTaskLambda implements RequestHandler<Object, String> {

    private final String clusterName = "devcluster";
    private final String taskDefinitionArn = "arn:aws:ecs:us-east-1:631256455463:task-definition/Java-daksh:2";

    private final AmazonECS ecsClient = AmazonECSClientBuilder.defaultClient();

    @Override
    public String handleRequest(Object input, Context context) {
        try {
            // Specify the ECS task details
            RunTaskRequest runTaskRequest = new RunTaskRequest()
                    .withCluster(clusterName)
                    .withTaskDefinition(taskDefinitionArn)
                    .withCount(1); // Number of tasks to run

            // Start the ECS task
            RunTaskResponse runTaskResponse = ecsClient.runTask(runTaskRequest);

            // Log task details (optional)
            String taskArn = runTaskResponse.getTasks().get(0).getTaskArn();
            System.out.println("ECS Task ID: " + taskArn);

            return "ECS task started successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error starting ECS task";
        }
    }
}
