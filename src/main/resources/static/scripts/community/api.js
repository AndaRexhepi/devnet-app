class CommunityApi{
    constructor() {
        this.api = "/api/v1/community";
    }

    async getCommunities() {
        const response = await fetch(this.api);
        return response.json();
    }

    async getCommunity(id) {
        const response = await fetch(this.api + "/" + id);
        return response.json();
    }

    async getDefaultUser() {
        const response = await fetch(this.api + "/default-user");
        return response.json();
    }

    async addCommunity(community) {
        const response = await fetch(this.api, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(community)
        });
        return response.text();
    }

    async updateCommunity(community) {
        const response = await fetch(this.api, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(community)
        });
        return response.text();
    }

    async deleteCommunity(id) {
        const response = await fetch(this.api + "/" + id, {
            method: "DELETE"
        });
        return response.text();
    }

}