class CommunityApp {
    constructor() {
        this.communityApi = new CommunityApi();
    }

    start() {
        this.loadAllCommunities();
    }

    loadAllCommunities() {
        this.communityApi.getCommunities().then(communities => {
            const communityList = document.getElementById("communityList");
            communities.forEach(community => {
                const communityElement = this.createCommunityElement(community);
                communityList.appendChild(communityElement);
            });
        });
    }

    createCommunityElement(community) {
        const communityElement = document.createElement("div");
        communityElement.classList.add("community");
        communityElement.innerHTML = `
            <img src="${community.imageUrl}" alt="community image" class="community-img">
            <div class="text-div">
                <p>${community.description}</p>
            </div>
            <div class="community-options">
                <div class="three-dots" onclick="toggleOptions(this)">
                    <span>&#x2022;&#x2022;&#x2022;</span>
                </div>
                <div class="options" style="display: none;">
                    <a href="#" th:href="@{/{id}(id=${community.id})}" onclick="openModal()" data-id="${community.id}">Delete</a>
                </div>
            </div>
            <div id="deleteConfirmationModal" class="modal" style="display: none;">
                <div class="modal-content">
                    <h2>Confirm Deletion</h2>
                    <p>Are you sure you want to delete this community?</p>
                    <button onclick="closeModal()">Cancel</button>
                    <button onclick="deleteCommunity(${community.id})">Delete</button>
                </div>
            </div>
        `;
        return communityElement;
    }
}

