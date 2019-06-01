import os
import pandas as pd
import file_processor as fp
import numpy as np
from GRNPlotter import GRNPlotter
import tools.io_tools as io_tools
import tools.mod_tools as mod_tools


class CSVFileOpener:
    def __init__(self):
        pass

    @staticmethod
    def get_column_values_of_an_trial(path, column):
        csv_files = []
        for root, dirs, files in os.walk(path):
            for a_file in files:
                if a_file.endswith(".csv"):
                    csv_files.append(root + os.sep + a_file)

        if len(csv_files) == 1:
            a_df = pd.read_csv(csv_files[0], '\t')
            return a_df[column].tolist()
        else:
            pass
            # raise RuntimeError("Error at " + path + ": No or multiple CSV files detected. ")

    def get_properties_of_each_generation_in_a_whole_experiment(self, exp_path, column, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)[:sample_size]
        all_trails = []
        for a_trail_dir in trial_dirs:
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, column)
            if a_dir_list is not None:
                all_trails.append(np.array(a_dir_list))
        return np.mean(np.array(all_trails), axis=0)

    def get_properties_of_each_generation_in_a_whole_experiment_with_stdev(self, exp_path, column, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)[:sample_size]
        all_trails = []
        for a_trail_dir in trial_dirs:
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, column)
            if a_dir_list is not None:
                all_trails.append(np.array(a_dir_list))
        return np.mean(np.array(all_trails), axis=0), np.std(np.array(all_trails), axis=0)

    def get_mod_of_each_generation_in_a_whole_exp_with_stdev(self, exp_path, column, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)[:sample_size]
        all_trails = []
        for a_trail_dir in trial_dirs:
            grn_plotter = GRNPlotter()
            phenotypes = grn_plotter.get_grn_phenotypes(a_trail_dir)
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, column)
            if a_dir_list is not None:
                trial_edges = list([io_tools.count_number_of_edges(a_phe) for a_phe in phenotypes])
                normed_qs = mod_tools.normalize_mod_q(a_dir_list, trial_edges)
                all_trails.append(np.array(normed_qs))
        return np.mean(np.array(all_trails), axis=0), np.std(np.array(all_trails), axis=0)


if __name__ == '__main__':
    pass
