export interface Task {
  id?: string;
  name?: string;
  description?: string;
  isCompleted?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
  isSaving?: boolean;
  isCompleting?: boolean;
  isDeleting?: boolean;
}

export const taskFromJSON = (json: any): Task => {
  return {
    id: json.id || crypto.randomUUID(),
    name: json.name || '',
    description: json.description || '',
    isCompleted: json.isCompleted || false,
    createdAt: new Date(json.createdAt),
    updatedAt: new Date(json.updatedAt),
  };
}
