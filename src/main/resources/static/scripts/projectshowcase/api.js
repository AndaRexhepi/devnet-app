class ProjectApi{
    constructor(){
        this.api = "/api/v1/project";
    }

    async getProjects(){
        const response = await fetch(this.api);
        return response.json();
    }

    async addProject(project){
        const response = await fetch(this.api, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(project)
        });
        return response.text();
    }

    async updateProject(project){
        const response = await fetch(this.api, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(project)
        });
        return response.text();
            }


    async deleteProject(id){
        const response = await fetch(this.api + "/" + id, {
            method: 'DELETE',
        });
        return response.text();
    }
}