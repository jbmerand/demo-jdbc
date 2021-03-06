package fr.diginamic.demo_jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.demo_jdbc.exceptions.TechnicalException;

/**
 * Contient l'ensemble des requêtes à opérer sur la base de données
 *
 */
public class Statements {

	/** LOGGER : Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(Statements.class);

	/**
	 * insère 4 articles dans la table ARTICLE (10€, 0.5€, 25€, 15€)
	 * 
	 * @param connexion connexion à la base de données
	 */
	public void insererArticles(Connection connexion) {
		Statement monStatement = null;
		Statement monStatement2 = null;
		Statement monStatement3 = null;
		Statement monStatement4 = null;
		try {
			monStatement = connexion.createStatement();
			monStatement2 = connexion.createStatement();
			monStatement3 = connexion.createStatement();
			monStatement4 = connexion.createStatement();
			int nb = monStatement.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('Enveloppes', 'Beau Bureau', 10)");
			int nb2 = monStatement2.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('Timbre', 'La Poste', 0.50)");
			int nb3 = monStatement2.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('Sac', 'Timberland', 25)");
			int nb4 = monStatement2.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('Casquette', 'Nike', 15)");
			LOGGER.info("OK: articles insérés");
		} catch (SQLException e) {
			LOGGER.error("Echec: connexion à la base de données", e);
			throw new TechnicalException("La connexion à la bdd a echoué.", e);
		} finally {
			if (monStatement != null && monStatement2 != null && monStatement3 != null && monStatement4 != null) {
				try {
					monStatement.close();
					monStatement2.close();
					monStatement3.close();
					monStatement4.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture des statements", e);
					throw new TechnicalException("Fermeture des statements echouée.", e);
				}
			}
		}
	}

	/**
	 * augmente les tarifs de 25% des articles de plus de 10€
	 * 
	 * @param connexion connexion à la base de données
	 */
	public void augmenterPrixDe25PourCent(Connection connexion) {
		Statement statement = null;
		try {
			statement = connexion.createStatement();
			int nb = statement.executeUpdate("UPDATE ARTICLE SET PRIX=PRIX*1.25 WHERE PRIX>10");
			LOGGER.info("OK: augmentation du prix de 25%");
		} catch (SQLException e) {
			LOGGER.error("Echec: connexion à la base de données", e);
			throw new TechnicalException("La connexion à la bdd a echoué.", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture des statements", e);
					throw new TechnicalException("Fermeture du statements echouée.", e);
				}
			}
		}
	}

	/**
	 * affiche tous les articles
	 * 
	 * @param connexion connexion à la base de données
	 */
	public void afficherLesArticles(Connection connexion) {
		Statement statement = null;
		ResultSet curseurArticles = null;
		try {
			statement = connexion.createStatement();
			curseurArticles = statement.executeQuery("SELECT * FROM ARTICLE");

			System.out.println("Articles présents dans la base de données : ");
			while (curseurArticles.next()) {
				int id = curseurArticles.getInt("Id");
				String designation = curseurArticles.getString("DESIGNATION");
				String fournisseur = curseurArticles.getString("FOURNISSEUR");
				Double prix = curseurArticles.getDouble("PRIX");
				System.out.println("id: " + id + " | " + "designation: " + designation + " | " + "fournisseur: "
						+ fournisseur + " | " + "prix: " + prix);
			}
			curseurArticles.close();
		} catch (SQLException e) {
			LOGGER.error("Echec: connexion à la base de données", e);
			throw new TechnicalException("La connexion à la bdd a echoué.", e);
		} finally {
			if (curseurArticles != null) {
				try {
					curseurArticles.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture du ResultSet.", e);
					throw new TechnicalException("Echec de la fermeture du ResultSet");
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture du statement", e);
					throw new TechnicalException("Fermeture du statements echouée.", e);
				}
			}
		}
	}

	/**
	 * extrait la moyenne des prix des articles et affiche cette moyenne
	 * 
	 * @param connexion connexion à la base de données
	 */
	public void extraireEtAfficherMoyennePrix(Connection connexion) {
		Statement statement = null;
		ResultSet curseurArticles = null;
		try {
			statement = connexion.createStatement();
			curseurArticles = statement.executeQuery("SELECT AVG(PRIX) AS MOYENNE FROM ARTICLE");

			System.out.println("Moyenne des prix des articles :");
			if (curseurArticles.next()) {
				Double moyenne = curseurArticles.getDouble("MOYENNE");
				System.out.println(moyenne);
			}
		} catch (SQLException e) {
			LOGGER.error("Echec: connexion à la base de données", e);
			throw new TechnicalException("La connexion à la bdd a echoué.", e);
		} finally {
			if (curseurArticles != null) {
				try {
					curseurArticles.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture du ResultSet", e);
					throw new TechnicalException("Echec de la fermeture du ResultSet");
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture du statement", e);
					throw new TechnicalException("Fermeture du statement echouée.", e);
				}
			}
		}
	}

	/**
	 * supprime tous les articles de la base de données
	 * 
	 * @param connexion
	 */
	public void supprimerIntegraliteArticles(Connection connexion) {
		Statement statement = null;
		try {
			statement = connexion.createStatement();
			int nb = statement.executeUpdate("TRUNCATE TABLE ARTICLE");
			LOGGER.info("OK: tous les articles ont été supprimés.");
		} catch (SQLException e) {
			LOGGER.error("Echec: connexion à la base de données", e);
			throw new TechnicalException("La connexion à la bdd a echoué.", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOGGER.error("Echec: fermeture du statement", e);
					throw new TechnicalException("Fermeture du statement echouée.", e);
				}
			}
		}
	}
}
