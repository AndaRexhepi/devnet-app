class ProjectApp {
    constructor() {
        this.deleteBtn = document.getElementById("deleteBtn");
        this.confirmDeleteBtn = document.getElementById("confirmDeleteBtn");
        this.projectApi = new ProjectApi();
    }


    loadAllProjects() {
        this.projectApi.getProjects().then(projects => {
            let projectList = document.querySelector(".project-section");
            projects.forEach(project => {
                let projectElement = this.createProjectElement(project);
                projectList.appendChild(projectElement);
            })
        })
    }

    createProjectElement(project) {
        let projectElement = document.createElement("div");
        projectElement.classList.add("project");
        projectElement.innerHTML = `
        <img src="${project.imageUrl}" alt="project image" class="project-img">
        <div class="text-div">
            <p>${project.description}</p>
        </div>
        <div class="project-options">
            <div class="three-dots" onclick="toggleOptions(this)">
                <span>&#x2022;&#x2022;&#x2022;</span>
            </div>
            <div class="options" style="display: none;">
                <a href="#" th:href="@{/edit_project/{id}(id=${project.id})}">Edit</a>
                <a href="#" th:href="@{/{id}(id=${project.id})}" onclick="openModal()" data-id="${project.id}">Delete</a>
            </div>
        </div>
         <div id="deleteConfirmationModal" class="modal" style="display: none;">
                <div class="modal-content">
                    <h2>Confirm Deletion</h2>
                    <p>Are you sure you want to delete this review?</p>
                    <button id="confirmDeleteBtn">Delete</button>
                    <button onclick="closeModal()">Cancel</button>
                </div>
            </div>
        `;
        return projectElement;
    }

    start() {
        this.loadAllProjects();
        this.deleteBtn.addEventListener("click", () => {
            openModal()
            this.confirmDeleteBtn.addEventListener("click", () => {
                let id = this.deleteBtn.getAttribute("data-id");
               this.projectApi.deleteProject(id);
            })
        })
    }

}
const app = new ProjectApp();
app.start();