package ca.etsmtl.log430.lab1;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * This class displays various types of information on projects and resources
 * (individually and as lists) to the screen.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.6, 2013-Sep-13
 */

/*
 * Modification Log
 * ************************************************************************
 * v2.0, V. Debris, 2014-Mai-13 - Ajout de la methode
 * displayProjectsPreviouslyAssignedToResource(Resource)
 * 
 * v1.6, R. Champagne, 2013-Sep-13 - Various refactorings for new lab.
 * 
 * v1.5, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2012-Feb-02 - Various refactorings for new lab.
 * 
 * v1.2, 2011-Feb-02, R. Champagne - Various refactorings, javadoc comments.
 * 
 * v1.1, 2002-May-21, R. Champagne - Adapted for use at ETS.
 * 
 * v1.0, 12/29/99, A.J. Lattanze - Original version.
 * ************************************************************************
 */

public class Displays {

	private int lineCount = 0;
	private int maxLinesDisplayed = 18;

	/**
	 * Counts the number of lines that has been printed. Once a set number of
	 * lines has been printed, the user is asked to press the enter key to
	 * continue. This prevents lines of text from scrolling off of the page.
	 * 
	 * @param linesToAdd
	 */
	private void lineCheck(int linesToAdd) {

		Termio terminal = new Termio();

		if (lineCount >= maxLinesDisplayed) {

			lineCount = 0;
			System.out.print("\n*** Press Enter To Continue ***");
			terminal.keyboardReadChar();

		} else {

			lineCount += linesToAdd;

		} // if

	} // LineCheck

	/**
	 * Displays a resource object's elements as follows: Resource's first name,
	 * last name, ID number, role.
	 * 
	 * Note that the projects previously assigned to the resource and the
	 * projects assigned to the resource in this execution of the system are not
	 * displayed.
	 * 
	 * @param resource
	 */
	public void displayResource(Resource resource) {

		System.out.println(resource.getID() + " " + resource.getFirstName()
				+ " " + resource.getLastName() + " " + resource.getRole());
	}

	/**
	 * Displays a project object's elements as follows: ID, name, start date,
	 * end date, and priority. Note that the resources assigned to the project
	 * are not listed by this method.
	 * 
	 * @param project
	 */
	public void displayProject(Project project) {
		System.out.println(project.getID() + " " + project.getProjectName()
				+ " " + project.getStartDate() + " " + project.getEndDate()
				+ " " + project.getPriority());

	}

	/**
	 * Lists the resources that have been assigned to the project.
	 * 
	 * @param project
	 */
	public void displayResourcesAssignedToProject(Project project) {

		boolean done;
		Resource resource;

		System.out.println("\nResources assigned to: " + " " + project.getID()
				+ " " + project.getProjectName() + " :");
		lineCheck(1);

		System.out
				.println("===========================================================");
		lineCheck(1);

		project.getResourcesAssigned().goToFrontOfList();
		done = false;

		while (!done) {

			resource = project.getResourcesAssigned().getNextResource();

			if (resource == null) {

				done = true;

			} else {

				displayResource(resource);

			} // if

		} // while

	}

	/**
	 * Lists the projects currently assigned to a resource during this session.
	 * 
	 * @param resource
	 */
	public void displayProjectsAssignedToResource(Resource resource) {

		boolean done;
		Project project;

		System.out.println("\nProjects assigned (in this session) to : "
				+ resource.getFirstName() + " " + resource.getLastName() + " "
				+ resource.getID());
		lineCheck(2);
		System.out
				.println("========================================================= ");
		lineCheck(1);

		resource.getProjectsAssigned().goToFrontOfList();
		done = false;

		while (!done) {

			project = resource.getProjectsAssigned().getNextProject();

			if (project == null) {

				done = true;

			} else {

				displayProject(project);
				lineCheck(2);

			} // if

		} // while

	}

	/**
	 * Lists the projects previously assigned to a resource before this session.
	 * 
	 * @param resource
	 */
	public void displayProjectsPreviouslyAssignedToResource(Resource resource) {

		boolean done;
		Project project;

		System.out.println("\nProjects assigned (before this session) to : "
				+ resource.getFirstName() + " " + resource.getLastName() + " "
				+ resource.getID());
		lineCheck(2);
		System.out
				.println("========================================================= ");
		lineCheck(1);

		resource.getPreviouslyAssignedProjectList().goToFrontOfList();
		done = false;

		while (!done) {

			project = resource.getPreviouslyAssignedProjectList()
					.getNextProject();

			if (project == null) {

				done = true;

			} else {

				displayProject(project);
				lineCheck(2);

			} // if

		} // while

	}

	/**
	 * Displays the resources in a resource list. Displays the same information
	 * that is listed in the displayResource() method listed above.
	 * 
	 * @param list
	 */
	public void displayResourceList(ResourceList list) {

		boolean done;
		Resource resource;

		System.out.print("\n");
		lineCheck(1);

		list.goToFrontOfList();

		done = false;

		while (!done) {

			resource = list.getNextResource();

			if (resource == null) {

				done = true;

			} else {

				displayResource(resource);
				lineCheck(1);

			} // if

		} // while

	}

	public void displayAllRolesToProject(Project project, ResourceList list) {
		String idProject = project.getID();

		System.out.print("\n");
		lineCheck(1);

		list.goToFrontOfList();

		Resource resource;
		boolean done = false;
		ArrayList<String> listRoles = new ArrayList<String>();
		while (!done) {

			resource = list.getNextResource();

			if (resource == null) {
				done = true;
			} else {
				boolean contains = resource.containsProject(idProject);
				if (contains && !listRoles.contains(resource.getRole()))
					listRoles.add(resource.getRole());

			} // if

		} // while

		System.out.println("Role for the project " + project.getID() + " :");
		for (String nomRole : listRoles) {
			System.out.println("-" + nomRole);

			lineCheck(1);
		}
	}

	/**
	 * Displays the projects in a project list. Displays the same information
	 * that is listed in the displayProject() method listed above.
	 * 
	 * @param list
	 */
	public void displayProjectList(ProjectList list) {

		boolean done;
		Project project;

		System.out.print("\n");
		lineCheck(1);

		list.goToFrontOfList();
		done = false;

		while (!done) {

			project = list.getNextProject();

			if (project == null) {

				done = true;

			} else {

				displayProject(project);
				lineCheck(1);

			} // if

		} // while

	}

	/**
	 * Vérifie si une ressource n'est pas surchagée
	 */
	public String checkRessourceSurchage(Resource resource, int chargeTrav,String projectId, String dateDeb, String dateFin) {
		String messageErreur = "";
		boolean done2 = false;
		boolean done = false;
		ProjectList projects = new ProjectList();

		// copie de project List
		ProjectList pr = new ProjectList();

		projects.goToFrontOfList();
		// Projets de la resources

		ProjectList projectsAssigned = resource.getProjectsAssigned();
		projectsAssigned.goToFrontOfList();
		Project projectAssigned;
		while (!done) {
			projectAssigned = projectsAssigned.getNextProject();
			if (projectAssigned == null) {
				done = true;
			} else {
				projects.addProject(projectAssigned);
				pr.addProject(projectAssigned);
			}
		}

		done = false;
		// Ajout des projets déjà dans le fichier
		ProjectList oldProjects = resource.getPreviouslyAssignedProjectList();
		oldProjects.goToFrontOfList();
		Project oldProject;
		while (!done) {
			oldProject = oldProjects.getNextProject();
			if (oldProject == null) {
				done = true;
			} else {
				projects.addProject(oldProject);
				pr.addProject(oldProject);
			}
		}
		// fin ajout

		// Parcours des projets
		projects.goToFrontOfList();
		Project project;
		int chargeTravail = chargeTrav;
		while (!done2) {
			project = projects.getNextProject();

			if (project == null) {
				done2 = true;
			} else {
				// On récupère les projets qui ont lieu en même temps que ce
				// projet
				if (project.getID().equals(projectId)) {
					if (project.getPriority().equals("H"))
						chargeTravail += 100;
					else if (project.getPriority().equals("M"))
						chargeTravail += 50;
					else if (project.getPriority().equals("L"))
						chargeTravail += 25;
				} else {
					ArrayList<String> alProjet = pr.findProjectSameDate(
							dateDeb, dateFin, projectId);
					if (alProjet.size() > 0) {

						for (String id : alProjet) {
							int charge = 0;
							if (projects.findProjectByID(id).getPriority()
									.equals("H"))
								charge = 100;
							else if (projects.findProjectByID(id).getPriority()
									.equals("M"))
								charge = 50;
							else if (projects.findProjectByID(id).getPriority()
									.equals("L"))
								charge = 25;
							chargeTravail += charge;
						}

					}
				}
				if (chargeTravail > 100) {
					messageErreur += "La ressource " + resource.getID()
							+ " est surchargée\n";
					// on passe à la prochaine ressource
					done2 = true;
				}

			} // if

		} // while

		return messageErreur;
	}
} // Display