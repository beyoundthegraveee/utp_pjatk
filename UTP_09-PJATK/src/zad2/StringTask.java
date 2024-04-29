package zad2;

public class StringTask implements Runnable{


    String line;

    TaskState taskState;

    String result = "";

    int count;

    public StringTask(String line, int count) {
        this.line = line;
        this.count = count;
        this.taskState = TaskState.CREATED;
    }

    @Override
    public void run() {
        taskState = TaskState.RUNNING;
        for (int i = 0; i <  count*4; i++) {
            try {
                result+= line;
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            }
            if(Thread.interrupted()){
                taskState = TaskState.ABORTED;
                return;
            }
        }

        result = result.substring(0,result.length()/4);
        taskState = TaskState.READY;
    }



    public String getResult(){
        return result;
    }

    public TaskState  getState() {
        return taskState;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void abort(){
        if(taskState == TaskState.RUNNING){
            taskState = TaskState.ABORTED;
            Thread.currentThread().interrupt();
        }

    }

    public boolean isDone() {
        return taskState == TaskState.READY || taskState == TaskState.ABORTED;
    }
}
