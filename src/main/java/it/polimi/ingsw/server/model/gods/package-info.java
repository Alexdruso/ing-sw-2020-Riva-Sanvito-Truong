/**
 * This package contains the implementation of the Gods of Santorini.
 * Each god is a class that contains all the information regarding the god itself, like as if it would be a physical card.
 * This package exports the God interface and the GodCards enum to allow clients to access the gods.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * God god1 = GodCards.APOLLO.getGod();
 * God god2 = GodCards.valueOf("APOLLO").getGod();
 * // god1 == god2
 * }
 * </pre>
 *
 * @see it.polimi.ingsw.server.model.gods.God
 * @see it.polimi.ingsw.server.model.gods.GodCard
 */
package it.polimi.ingsw.server.model.gods;