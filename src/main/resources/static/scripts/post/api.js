class PostApi {
    constructor() {
        this.api = "/api/v1/posts";

    }

    async findAll() {
        const response = await fetch(this.api);
        return response.json();
    }

    async findById(id) {
        const response = await fetch(`${this.api}/${id}`);
        return response.json();
    }

    async getDefaultUser() {
        const response = await fetch(`${this.api}/default`);
        return response.json();
    }

    async add(post) {
        const response = await fetch(this.api, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(post)
        });
        return response.json();
    }

    async modify(post, id) {
        const response = await fetch(`${this.api}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(post)
        });
        return response.json();
    }

    async delete(id) {
        const response = await fetch(`${this.api}/${id}`, {
            method: 'DELETE'
        });
        return response.status === 204;
    }


}