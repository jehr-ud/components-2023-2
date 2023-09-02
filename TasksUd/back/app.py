from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from pymongo import MongoClient
from bson import ObjectId

app = FastAPI()

# MongoDB Configuration
mongo_uri = "mongodb://localhost:27017"
client = MongoClient(mongo_uri)
db = client['task_db']
tasks_collection = db['tasks']


# Pydantic Model for Task
class TaskCreate(BaseModel):
    name: str
    description: str
    create_at: str
    due_at: str
    state: str


class Task(TaskCreate):
    id: str


# Create a new task
@app.post("/tasks/", response_model=Task)
async def create_task(task: TaskCreate):
    result = tasks_collection.insert_one(task.model_dump())
    task.id = str(result.inserted_id)
    return task


# Get all tasks
@app.get("/tasks/", response_model=list[Task])
async def read_tasks():
    tasks = list(tasks_collection.find())
    return tasks


# Get a task by ID
@app.get("/tasks/{task_id}", response_model=Task)
async def read_task(task_id: str):
    task = tasks_collection.find_one({"_id": ObjectId(task_id)})
    if task:
        task['id'] = str(task.pop('_id'))
        return task
    raise HTTPException(status_code=404, detail="Task not found")


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="localhost", port=8000)
