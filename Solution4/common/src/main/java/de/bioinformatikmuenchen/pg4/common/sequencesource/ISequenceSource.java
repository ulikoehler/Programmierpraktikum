package de.bioinformatikmuenchen.pg4.common.sequencesource;

import de.bioinformatikmuenchen.pg4.common.Sequence;

/**
 *
 * @author koehleru
 */
public interface ISequenceSource {
    Sequence getSequence(String id);
}
